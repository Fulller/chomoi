package com.ecommerce.chomoi.service;

import com.ecommerce.chomoi.dto.address.AddressRequest;
import com.ecommerce.chomoi.dto.address.AddressResponse;
import com.ecommerce.chomoi.entities.Account;
import com.ecommerce.chomoi.entities.Address;
import com.ecommerce.chomoi.exception.AppException;
import com.ecommerce.chomoi.mapper.AddressMapper;
import com.ecommerce.chomoi.repository.AddressRepository;
import com.ecommerce.chomoi.security.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressService {
    AddressRepository addressRepository;
    SecurityUtil securityUtil;
    AddressMapper addressMapper;

    public AddressResponse create(AddressRequest request){
        Account account = securityUtil.getAccount();
        Address address = addressMapper.toAddress(request);
        address.setAccount(account);
        addressRepository.save(address);
        return addressMapper.toAddressResponse(address);
    }

    public AddressResponse get(String id){
        Address address = addressRepository.findById(id)
                .orElseThrow(
                        () -> new AppException(HttpStatus.NOT_FOUND,"Address not found", "address-e-01")
                );
        return addressMapper.toAddressResponse(address);
    }

    public List<AddressResponse> getAll(){
         List<Address> addresses = addressRepository.findAll();
        return addressMapper.toListAddressResponse(addresses);
    }

    public AddressResponse update(String id, AddressRequest request){


        Address address = addressRepository.findById(id)
                .orElseThrow(
                        () -> new AppException(HttpStatus.NOT_FOUND,"Address not found", "address-e-02")
                );
        addressMapper.updateAddress(address, request);
        addressRepository.save(address);

        return addressMapper.toAddressResponse(address);
    }

    public void delete(String id){
        addressRepository.deleteById(id);
    }
}
