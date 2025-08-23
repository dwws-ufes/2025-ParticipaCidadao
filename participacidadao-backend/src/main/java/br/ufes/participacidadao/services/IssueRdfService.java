package br.ufes.participacidadao.services;

import br.ufes.participacidadao.models.IssueModel;
import br.ufes.participacidadao.models.DadosEnriquecidos;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.vocabulary.RDF;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;

@Service
public class IssueRdfService {
	private static final String NS = "http://example.org/issue/";

	public String issuesToRdf(List<IssueModel> issues) {
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("issue", NS);

		for (IssueModel issue : issues) {
			Resource issueRes = model.createResource(NS + issue.getId())
					.addProperty(RDF.type, model.createResource(NS + "Issue"));
			if (issue.getTitle() != null)
				issueRes.addProperty(model.createProperty(NS, "title"), issue.getTitle());
			if (issue.getDescription() != null)
				issueRes.addProperty(model.createProperty(NS, "description"), issue.getDescription());
			if (issue.getStatus() != null)
				issueRes.addProperty(model.createProperty(NS, "status"), issue.getStatus().toString());
			if (issue.getCreatedAt() != null)
				issueRes.addProperty(model.createProperty(NS, "createdAt"), issue.getCreatedAt().toString());
			if (issue.getCity() != null)
				issueRes.addProperty(model.createProperty(NS, "city"), issue.getCity());
			if (issue.getNeighborhood() != null)
				issueRes.addProperty(model.createProperty(NS, "neighborhood"), issue.getNeighborhood());
			if (issue.getStreet() != null)
				issueRes.addProperty(model.createProperty(NS, "street"), issue.getStreet());
			if (issue.getRefPoint() != null)
				issueRes.addProperty(model.createProperty(NS, "refPoint"), issue.getRefPoint());

			DadosEnriquecidos dados = issue.getDadosEnriquecidos();
			if (dados != null) {
				if (dados.getPopulation() != null)
					issueRes.addProperty(model.createProperty(NS, "population"), dados.getPopulation().toString());
				if (dados.getArea() != null)
					issueRes.addProperty(model.createProperty(NS, "area"), dados.getArea().toString());
				if (dados.getPibPerCapita() != null)
					issueRes.addProperty(model.createProperty(NS, "pibPerCapita"), dados.getPibPerCapita().toString());
				if (dados.getUf() != null)
					issueRes.addProperty(model.createProperty(NS, "uf"), dados.getUf());
				if (dados.getRegiao() != null)
					issueRes.addProperty(model.createProperty(NS, "regiao"), dados.getRegiao());
				if (dados.getDbpediaUri() != null)
					issueRes.addProperty(model.createProperty(NS, "dbpediaUri"), dados.getDbpediaUri());
				if (dados.getWikidataUri() != null)
					issueRes.addProperty(model.createProperty(NS, "wikidataUri"), dados.getWikidataUri());
			}
		}
		StringWriter out = new StringWriter();
		model.write(out, "TURTLE");
		return out.toString();
	}
}
