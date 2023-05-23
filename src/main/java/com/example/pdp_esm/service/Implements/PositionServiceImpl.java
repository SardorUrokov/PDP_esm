package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.Position;
import com.example.pdp_esm.exception.ResourceNotFoundException;
import com.example.pdp_esm.repository.PositionRepository;
import com.example.pdp_esm.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Override
    public ApiResponse<?> createPosition(String positionName) {
        boolean existByName = positionRepository.existsByName(positionName);

        if (!existByName) {
            Position position = new Position();
            position.setName(positionName);
            Position save = positionRepository.save(position);
            return ApiResponse.builder()
                    .message("Position Created")
                    .success(true)
                    .data(save)
                    .build();

        } else return ApiResponse.builder()
                .message("Such a Position is already Created")
                .success(false)
                .build();
    }

    @Override
    public ApiResponse<?> getAllPositions() {
        return ApiResponse.builder()
                .message("All Positions List")
                .success(true)
                .data(positionRepository.findAll())
                .build();
    }

    @Override
    public ApiResponse<?> getOnePosition(Long id) {

        Optional<Position> optionalPosition = Optional.ofNullable(positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position", "id", id)));
        Position position = optionalPosition.get();
        String message = "Position with " + id + " id";
        return new ApiResponse<>( message, true, position);
    }

    @Override
    public ApiResponse<?> deletePosition(Long id) {
        boolean exists = positionRepository.existsById(id);

        if (exists) {
            positionRepository.deleteById(id);
            return ApiResponse.builder()
                    .message("Position deleted!")
                    .success(true)
                    .build();

        } else return ApiResponse.builder()
                .message("Position not found!")
                .success(false)
                .build();
    }
}