package com.cholog_ai.closet_saver.domain.closet.service;

import com.cholog_ai.closet_saver.domain.closet.model.ClosetItem;
import com.cholog_ai.closet_saver.domain.closet.repository.ClosetJsonRepository;
import com.cholog_ai.closet_saver.domain.closet.repository.ClosetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClosetService {

    private final ClosetRepository closetRepository;

    public List<ClosetItem> getAllItems(){
        return closetRepository.findAll();
    }

    public ClosetItem getItemById(Long id){
        return closetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 옷을 찾을 수 없습니다."));
    }

}
