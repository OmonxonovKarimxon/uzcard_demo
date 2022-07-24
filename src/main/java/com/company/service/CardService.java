package com.company.service;

import com.company.dto.card.CardCreateDTO;
import com.company.dto.card.CardDTO;
import com.company.dto.card.CardPhoneDTO;
import com.company.dto.card.CardStatusDTO;
import com.company.dto.client.ClientResponseDTO;
import com.company.entity.CardEntity;
import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import com.company.exps.ItemNotFoundException;
import com.company.repository.CardRepository;
import com.company.util.CreditCardNumberGenerator;
import com.company.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CreditCardNumberGenerator cardNumberGenerator;


    public CardDTO create(CardCreateDTO dto) {
        CardEntity entity = new CardEntity();
        entity.setBalance((long) 5000);
        entity.setClientId(dto.getClientId());
        entity.setExpiredDate(LocalDateTime.now().plusYears(4));
        entity.setNumber(cardNumberGenerator.generate("8600", 16));
        entity.setHiddenNumber("8600-****-****-"+entity.getNumber().substring(entity.getNumber().length()-4));
        entity.setCompanyId(SpringUtil.currentUser().getId());
        entity.setStatus(GeneralStatus.ACTIVE);
        cardRepository.save(entity);

        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(entity.getId());
        cardDTO.setNumber(entity.getNumber());
        cardDTO.setCreatedDate(entity.getCreatedDate());
        cardDTO.setExpiredDate(entity.getExpiredDate());
        cardDTO.setStatus(entity.getStatus());
        return cardDTO;
    }

    public void assignPhone(CardPhoneDTO dto) {
        get(dto.getId());
        cardRepository.updatePhone(dto.getPhone(), dto.getId());
    }

    public CardEntity get(String id) {
        return cardRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Card not found!");
        });
    }
    public CardEntity getByCardNumber(String cardNumber) {
        return cardRepository.findByNumber(cardNumber).orElseThrow(() -> {
            throw new ItemNotFoundException("Card not found!");
        });
    }

    public String changeStatus(CardStatusDTO dto) {
        get(dto.getId());
        GeneralRole role = SpringUtil.currentUser().getRole();
        if (role.equals(GeneralRole.ROLE_PAYMENT)) {
            cardRepository.changeStatus(GeneralStatus.BLOCK, dto.getId());
            return "Card blocked";
        }
        cardRepository.changeStatus(dto.getStatus(), dto.getId());
        return "Card successfully " + dto.getStatus().name();
    }

    public CardDTO getCardById(String id) {
        CardEntity entity = get(id);
        roleCheck(entity.getCompanyId());
        return getDTO(entity);
    }

    public List<CardDTO> getCardListByPhone(String phone) {
        List<CardEntity> entityList = cardRepository.findByPhone(phone);
        List<CardDTO> list = new LinkedList<>();
        GeneralRole role = SpringUtil.currentUser().getRole();
        entityList.forEach(entity -> {
            if (role.equals(GeneralRole.ROLE_PAYMENT)) {
                list.add(getDTO(entity));
            } else {
                CardDTO dto = checkCompanyRole(entity.getCompanyId(), entity);
                if (dto != null) {
                    list.add(dto);
                }
            }
        });
        return list;
    }


    private CardDTO checkCompanyRole(String cId, CardEntity entity) {
        String companyId = SpringUtil.currentUser().getId();
        if (Objects.equals(cId, companyId)) {
            return getDTO(entity);
        }
        return null;
    }


    public CardDTO getDTO(CardEntity entity) {
        CardDTO dto = new CardDTO();
        dto.setId(entity.getId());
        dto.setNumber(entity.getNumber());
        dto.setBalance(entity.getBalance());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPhone(entity.getPhone());
        dto.setExpiredDate(entity.getExpiredDate());
        dto.setStatus(entity.getStatus());

        ClientResponseDTO client = new ClientResponseDTO();
        client.setId(entity.getClientId());
        client.setName(entity.getClient().getName());
        client.setSurname(entity.getClient().getSurname());
        dto.setClient(client);
        return dto;
    }

    public List<CardDTO> getCardListByClientId(String clientId) {
        GeneralRole role = SpringUtil.currentUser().getRole();
        List<CardDTO> list = new LinkedList<>();
        List<CardEntity> entityList;
        if (role.equals(GeneralRole.ROLE_PAYMENT)) {
            entityList = cardRepository.findByClientId(clientId);
        } else {
            String cId = SpringUtil.currentUser().getId();
            entityList = cardRepository.findByClientIdAndCompanyId(clientId, cId);
        }
        entityList.forEach(entity -> {
            list.add(getDTO(entity));
        });
        return list;
    }

    public CardDTO getCardByNumber(String num) {
        Optional<CardEntity> optional = cardRepository.findByNumber(num);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Card not found!");
        }
        CardEntity entity = optional.get();
        roleCheck(entity.getCompanyId());
        return getDTO(entity);
    }

    private void roleCheck(String cId) {
        GeneralRole role = SpringUtil.currentUser().getRole();
        String companyId = SpringUtil.currentUser().getId();

        if (role.equals(GeneralRole.ROLE_BANK)) {
            if (!Objects.equals(cId, companyId)) {
                throw new ItemNotFoundException("Bu card boshqa bankka tegishli!");
            }
        }
    }

    public CardDTO getCardBalanceByNumber(String num) {
        Optional<CardEntity> optional = cardRepository.findByNumber(num);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Card not found!");
        }
        CardEntity entity = optional.get();
        roleCheck(entity.getCompanyId());

        CardDTO dto = new CardDTO();
        dto.setBalance(entity.getBalance());
        return dto;
    }
    public  void changeBalance(Long balance, String id ){
        cardRepository.changeBalance( balance,id);
    }
}
