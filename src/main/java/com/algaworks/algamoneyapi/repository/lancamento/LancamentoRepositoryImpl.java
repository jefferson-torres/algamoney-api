package com.algaworks.algamoneyapi.repository.lancamento;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.model.Lancamento_;
import com.algaworks.algamoneyapi.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		return query.getResultList();
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		List<Predicate> predicates = new LinkedList<>();

		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			String descricao = String.format("%%%s%%", lancamentoFilter.getDescricao());
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)), builder.lower(builder.literal(descricao))));
		}

		if (lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(Lancamento_.DATA_VENCIMENTO), lancamentoFilter.getDataVencimentoDe()));
		}

		if (lancamentoFilter.getDataVencimentoaAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Lancamento_.DATA_VENCIMENTO), lancamentoFilter.getDataVencimentoaAte()));
		}

		return predicates.toArray(Predicate[]::new);
	}

}
