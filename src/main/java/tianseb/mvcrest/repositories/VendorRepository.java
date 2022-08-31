package tianseb.mvcrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tianseb.mvcrest.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor,Long> {
}
