package com.cholog_ai.closet_saver.domain.closet.repository;

import com.cholog_ai.closet_saver.domain.closet.model.ClosetItem;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Profile("dev") // dev시에만 Mock Data를 위해 사용할 레포
@Repository
@Slf4j
public class ClosetJsonRepository implements ClosetRepository{
    private final List<ClosetItem> closetItems = new ArrayList<>();

    @Override
    public List<ClosetItem> findAll() {
        return new ArrayList<>(closetItems);
    }

    @Override
    public Optional<ClosetItem> findById(Long id) {
        return closetItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @PostConstruct // 서버 시작 시 Json 파일 로딩
    @Override
    public void loadInitialData() {
        try{
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getResourceAsStream("/mock/closet.json");


            // deserialize json data
            List<ClosetItem> items = mapper.readValue(
                    is,
                    new TypeReference<List<ClosetItem>>() {}
            );
            closetItems.addAll(items);
            log.info("Closet JSON Loaded : {} items", closetItems.size());

        }catch (Exception e){
            log.error("Failed to load JSON", e);
        }
    }
}
