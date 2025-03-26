package org.milton.auctionservice.repository;

import org.milton.auctionservice.models.AuctionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<AuctionOrder, Long> {
}
