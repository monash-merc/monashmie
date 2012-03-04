package edu.monash.merc.service.impl;

import edu.monash.merc.dao.impl.RegMetadataDAO;
import edu.monash.merc.domain.RegMetadata;
import edu.monash.merc.service.RegMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Simon Yu
 * @email Xiaoming.Yu@monash.edu
 *
 * Date: 5/01/12
 * Time: 2:17 PM
 * @version 1.0
 */
@Scope("prototype")
@Service
@Transactional
public class RegMetadataServiceImpl implements RegMetadataService {

    @Autowired
    private RegMetadataDAO regMetadataDao;

    public void setRegMetadataDao(RegMetadataDAO regMetadataDao) {
        this.regMetadataDao = regMetadataDao;
    }

    @Override
    public void saveRegMetadata(RegMetadata regMetadata) {
        this.regMetadataDao.add(regMetadata);
    }

    @Override
    public RegMetadata getRegMetadataById(long id) {
        return this.regMetadataDao.get(id);
    }

    @Override
    public void deleteRegMetadata(RegMetadata regMetadata) {
        this.regMetadataDao.remove(regMetadata);
    }

    @Override
    public void updateRegMetadata(RegMetadata regMetadata) {
        this.regMetadataDao.update(regMetadata);
    }

    @Override
    public RegMetadata getRegMetadatadByDatasetId(long dsId) {
        return this.regMetadataDao.getRegMetadatadByDatasetId(dsId);
    }
}
