package edu.monash.merc.dao.impl;

import edu.monash.merc.dao.HibernateGenericDAO;
import edu.monash.merc.domain.RegMetadata;
import edu.monash.merc.repository.IRegMetadataRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author Simon Yu
 * @email Xiaoming.Yu@monash.edu
 *
 * Date: 4/01/12
 * Time: 5:37 PM
 * @version 1.0
 */

@Scope("prototype")
@Repository
public class RegMetadataDAO extends HibernateGenericDAO<RegMetadata> implements IRegMetadataRepository {
    @Override
    public RegMetadata getRegMetadatadByDatasetId(long dsId) {
        Criteria criteria = this.session().createCriteria(this.persistClass);
        criteria.add(Restrictions.eq("datasetId", dsId));
        return (RegMetadata) criteria.uniqueResult();
    }
}
