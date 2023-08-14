package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.domain.Locadora;

@SuppressWarnings("unchecked")
public interface ILocadoraDAO extends CrudRepository<Locadora, Long>{

	Locadora findById(long id);
	
	Locadora findByCNPJ (String CNPJ);

	List<Locadora> findAll();
	
	Locadora save(Locadora locadora);

	void deleteById(Long id);

    // @Query("SELECT * FROM locadoras l WHERE l.empresa.cidade = :cidade AND FUNCTION('STR_TO_DATE', v.dataLimite, '%d/%m/%Y') >= CURRENT_DATE")
    // public List<Locadora> getVagasByCidade(@Param("cidade") String cidade);
}
