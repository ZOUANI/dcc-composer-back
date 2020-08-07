package ma.zs.dynamiquecall.dao;


import ma.zs.dynamiquecall.bean.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestDao extends JpaRepository<Request, Long> {
}


