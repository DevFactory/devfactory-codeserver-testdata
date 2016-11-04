package com.devfactory.codeserver.api.service;

import com.devfactory.codeserver.api.model.Revision;
import java.io.File;

/**
 * Interface to represent downloadable business methods.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public interface DownloadableService {

    File zipFileLocation(Revision revision);

}
