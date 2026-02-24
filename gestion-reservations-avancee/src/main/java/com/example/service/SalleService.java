package com.example.service;

import com.example.model.Salle;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SalleService {

    List<Salle> findAvailableRooms(LocalDateTime start, LocalDateTime end);

    List<Salle> searchRooms(Map<String, Object> criteria);

    List<Salle> getPaginatedRooms(int page, int size);

    int getTotalPages(int size);

    Salle getRoomById(Long id);

    List<Salle> getAllRooms();

    void saveRoom(Salle salle);

    void updateRoom(Salle salle);

    void deleteRoom(Salle salle);
}