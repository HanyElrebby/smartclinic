package com.smartinn.smartclinic.service;

import com.smartinn.smartclinic.domain.File;
import com.smartinn.smartclinic.repository.FileRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link File}.
 */
@Service
@Transactional
public class FileService {

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Save a file.
     *
     * @param file the entity to save.
     * @return the persisted entity.
     */
    public File save(File file) {
        log.debug("Request to save File : {}", file);
        return fileRepository.save(file);
    }

    /**
     * Partially update a file.
     *
     * @param file the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<File> partialUpdate(File file) {
        log.debug("Request to partially update File : {}", file);

        return fileRepository
            .findById(file.getId())
            .map(
                existingFile -> {
                    if (file.getFileName() != null) {
                        existingFile.setFileName(file.getFileName());
                    }
                    if (file.getFile() != null) {
                        existingFile.setFile(file.getFile());
                    }
                    if (file.getFileContentType() != null) {
                        existingFile.setFileContentType(file.getFileContentType());
                    }
                    if (file.getDateUpload() != null) {
                        existingFile.setDateUpload(file.getDateUpload());
                    }
                    if (file.getNote() != null) {
                        existingFile.setNote(file.getNote());
                    }
                    if (file.getCreatedBy() != null) {
                        existingFile.setCreatedBy(file.getCreatedBy());
                    }
                    if (file.getUpdatedBy() != null) {
                        existingFile.setUpdatedBy(file.getUpdatedBy());
                    }

                    return existingFile;
                }
            )
            .map(fileRepository::save);
    }

    /**
     * Get all the files.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<File> findAll(Pageable pageable) {
        log.debug("Request to get all Files");
        return fileRepository.findAll(pageable);
    }

    /**
     * Get one file by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<File> findOne(Long id) {
        log.debug("Request to get File : {}", id);
        return fileRepository.findById(id);
    }

    /**
     * Delete the file by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete File : {}", id);
        fileRepository.deleteById(id);
    }
}
