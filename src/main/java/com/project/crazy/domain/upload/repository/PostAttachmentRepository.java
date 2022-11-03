package com.project.crazy.domain.upload.repository;

import com.project.crazy.domain.upload.entity.PostAttachment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostAttachmentRepository extends CrudRepository<PostAttachment, Long> {
}
