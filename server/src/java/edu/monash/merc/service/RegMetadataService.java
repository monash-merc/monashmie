package edu.monash.merc.service;

import edu.monash.merc.domain.RegMetadata;

/**
 * @author Simon Yu
 * @email Xiaoming.Yu@monash.edu
 *
 * Date: 5/01/12
 * Time: 2:10 PM
 * @version 1.0
 */
public interface RegMetadataService {

    public void saveRegMetadata(RegMetadata regMetadata);

    public RegMetadata getRegMetadataById(long id);

    public void deleteRegMetadata(RegMetadata regMetadata);

    public void updateRegMetadata(RegMetadata regMetadata);

    public RegMetadata getRegMetadatadByDatasetId(long dsId);
}
