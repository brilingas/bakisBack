package lt.brilingas.guestregistry.dao.impl.mongo.dao.mapper;

import lt.brilingas.guestregistry.dao.impl.mongo.entity.location.LocationEntity;
import lt.brilingas.guestregistry.data.dto.location.LocationDTO;
import java.util.LinkedList;
import java.util.List;

public class LocationMapper {
    public static LocationDTO toDTO(LocationEntity locationEntity) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(locationEntity.getId());
        locationDTO.setContactPerson(locationEntity.getContactPerson());
        locationDTO.setName(locationEntity.getName());
        locationDTO.setStatus(locationEntity.getStatus());
        locationDTO.setType(locationEntity.getType());
        locationDTO.setAddress(locationEntity.getAddress());
        return locationDTO;
    }

    public static LocationEntity toEntity(LocationDTO locationDTO) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setId(locationDTO.getId());
        locationEntity.setContactPerson(locationDTO.getContactPerson());
        locationEntity.setName(locationDTO.getName());
        locationEntity.setStatus(locationDTO.getStatus());
        locationEntity.setType(locationDTO.getType());
        locationEntity.setAddress(locationDTO.getAddress());
        return locationEntity;
    }

    public static List<LocationDTO> toDTOLinkedList(List<LocationEntity> listEntity) {
        List<LocationDTO> DTOList = new LinkedList<>();
        for(LocationEntity locationEntity : listEntity) {
            DTOList.add(LocationMapper.toDTO(locationEntity));
        }
        return DTOList;
    }
}
