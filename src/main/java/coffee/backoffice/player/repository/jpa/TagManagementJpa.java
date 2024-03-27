package coffee.backoffice.player.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import coffee.backoffice.player.model.TagManagement;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagManagementJpa extends JpaRepository<TagManagement, Long> {

    List<TagManagement> findAll();

    Optional<TagManagement> findById(Long Id);
    Optional<TagManagement> findByTagCode(String code);

    TagManagement save(TagManagement data);
    default Page<TagManagement> findPaginate(final Pageable pageable) {
        return  findAll(pageable);
    }

}
