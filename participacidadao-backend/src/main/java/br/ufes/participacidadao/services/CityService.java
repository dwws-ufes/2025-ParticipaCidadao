package br.ufes.participacidadao.services;

import br.ufes.participacidadao.dto.CityDTO;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private static final String ENDPOINT = "https://dbpedia.org/sparql";

    public CityDTO fetchByName(String name) {
        String q = """
            PREFIX dbo:  <http://dbpedia.org/ontology/>
            PREFIX dbr:  <http://dbpedia.org/resource/>
            PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
            PREFIX geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#>
            SELECT ?city ?label ?abstract ?stateLabel ?population ?area ?lat ?lng
            WHERE {
              ?city a dbo:PopulatedPlace ;
                    dbo:country dbr:Brazil ;
                    rdfs:label ?label .
              FILTER (langMatches(lang(?label), "pt"))
              FILTER (
                lcase(str(?label)) = lcase(str(?name)) ||
                contains(lcase(str(?label)), lcase(str(?name)))
              )
              OPTIONAL { ?city dbo:abstract ?abstract . FILTER (langMatches(lang(?abstract), "pt")) }
              OPTIONAL {
                { ?city dbo:state ?state } UNION { ?city dbo:isPartOf ?state }
                ?state rdfs:label ?stateLabel . FILTER (langMatches(lang(?stateLabel), "pt"))
              }
              OPTIONAL { ?city dbo:populationTotal ?population }
              OPTIONAL { ?city dbo:areaTotal ?area }
              OPTIONAL { ?city geo:lat ?lat }
              OPTIONAL { ?city geo:long ?lng }
            }
            ORDER BY DESC(?population)
            LIMIT 1
            """;

        ParameterizedSparqlString pss = new ParameterizedSparqlString(q);
        pss.setLiteral("name", name);
        Query query = pss.asQuery();

        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, query)) {
            ResultSet rs = qexec.execSelect();
            if (!rs.hasNext()) return null;

            QuerySolution s = rs.next();
            return new CityDTO(
                getUri(s, "city"),
                getStr(s, "label"),
                getStr(s, "stateLabel"),
                getLong(s, "population"),
                getDouble(s, "area"),
                getDouble(s, "lat"),
                getDouble(s, "lng"),
                getStr(s, "abstract")
            );
        }
    }

    private static String getUri(QuerySolution s, String var) {
        RDFNode n = s.get(var);
        return n != null && n.isResource() ? n.asResource().getURI() : null;
    }
    private static String getStr(QuerySolution s, String var) {
        RDFNode n = s.get(var);
        return n == null ? null : (n.isLiteral() ? n.asLiteral().getString() : n.toString());
    }
    private static Long getLong(QuerySolution s, String var) {
        RDFNode n = s.get(var);
        try { return (n != null && n.isLiteral()) ? n.asLiteral().getLong() : null; } catch (Exception e) { return null; }
    }
    private static Double getDouble(QuerySolution s, String var) {
        RDFNode n = s.get(var);
        try { return (n != null && n.isLiteral()) ? n.asLiteral().getDouble() : null; } catch (Exception e) { return null; }
    }
}