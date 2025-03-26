package org.milton.auctionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.milton.auctionservice.models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByName(String name);
}
