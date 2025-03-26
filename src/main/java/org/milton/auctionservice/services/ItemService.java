package org.milton.auctionservice.services;

import org.milton.auctionservice.exeptions.ResourceNotFoundException;
import org.milton.auctionservice.models.Item;
import org.milton.auctionservice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item updateItem(Long id, Item itemDetails) {
        return itemRepository.findById(id).map(item -> {
            item.setName(itemDetails.getName());
            item.setDescription(itemDetails.getDescription());
            item.setStartingPrice(itemDetails.getStartingPrice());
            item.setCurrentPrice(itemDetails.getCurrentPrice());
            return itemRepository.save(item);
        }).orElseThrow(() -> new ResourceNotFoundException("Item not found with id " + id));
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
