package edu.monash.merc.repository;

import edu.monash.merc.domain.RegMetadata;

/**
 * @author Simon Yu
 * @email Xiaoming.Yu@monash.edu
 *
 * Date: 4/01/12
 * Time: 5:32 PM
 * @version 1.0
 */
public interface IRegMetadataRepository {

   public RegMetadata getRegMetadatadByDatasetId(long dsId);

}
