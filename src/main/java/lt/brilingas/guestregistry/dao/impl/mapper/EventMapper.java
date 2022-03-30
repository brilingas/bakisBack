package lt.brilingas.guestregistry.dao.impl.mapper;

import lt.brilingas.guestregistry.dao.impl.entity.event.EventEntity;
import lt.brilingas.guestregistry.data.dto.event.EventDTO;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;

public class EventMapper {
    public static EventDTO toDTO(EventEntity eventEntity) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(eventEntity.getId());
        eventDTO.setLocationId(eventEntity.getLocationId());
        eventDTO.setEventName(eventEntity.getEventName());
        eventDTO.setParticipantsNumber(eventEntity.getParticipantsNumber());
        if (eventEntity.getDate() == null) {
            eventDTO.setDate(null);
        } else {
            eventDTO.setDate(eventEntity.getDate().atZone(ZoneOffset.UTC));
        }
        return eventDTO;
    }

    public static EventEntity toEntity(EventDTO eventDTO) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(eventDTO.getId());
        eventEntity.setLocationId(eventDTO.getLocationId());
        eventEntity.setEventName(eventDTO.getEventName());
        eventEntity.setParticipantsNumber(eventDTO.getParticipantsNumber());
        if (eventDTO.getDate() == null) {
            eventEntity.setDate(null);
        } else {
            eventEntity.setDate(eventDTO.getDate().toInstant());
        }
        return eventEntity;
    }

    public static List<EventDTO> toDTOLinkedList(List<EventEntity> listEntity) {
        List<EventDTO> listDTO = new LinkedList<>();
        for(EventEntity eventEntity : listEntity) {
            listDTO.add(EventMapper.toDTO(eventEntity));
        }
        return listDTO;
    }
}
