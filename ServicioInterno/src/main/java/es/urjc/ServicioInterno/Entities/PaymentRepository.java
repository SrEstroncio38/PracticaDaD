package es.urjc.ServicioInterno.Entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
	
	List<Payment> findByUser(User user);
	List<Payment> findByOwnerName(String ownerName);
	List<Payment> findByCardNumber(String cardNumber);

}
