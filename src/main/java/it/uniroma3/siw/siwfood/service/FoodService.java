package it.uniroma3.siw.siwfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.siwfood.model.Ricetta;
import it.uniroma3.siw.siwfood.repository.FoodRepository;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public Ricetta findById(int id) {
        return foodRepository.findById(((long) id)).get();
    }

    public Iterable<Ricetta> findAll() {
        return foodRepository.findAll();
    }
}