package com.fast.food.addressservice.service;

import com.fast.food.addressservice.entity.Address;
import com.fast.food.addressservice.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAddressesByUserId(Long userId) {
        logger.info("Fetching addresses for userId={}", userId);
        return addressRepository.findByUserId(userId);
    }

    public Optional<Address> getAddressById(Long id) {
        logger.info("Fetching address by id={}", id);
        return addressRepository.findById(id);
    }

    @Transactional
    public Address saveAddress(Address address) {
        logger.info("Saving address for userId={}, address details={}", address.getUserId(), address);

        if (Boolean.TRUE.equals(address.getDefault())) {
            logger.info("Setting address as default for userId={}", address.getUserId());
            setDefaultAddress(address.getUserId(), address.getId());
        }

        Address savedAddress = addressRepository.save(address);
        logger.info("Address saved with id={}", savedAddress.getId());
        return savedAddress;
    }

    @Transactional
    public Address updateAddress(Long id, Address addressDetails) {
        logger.info("Updating address with id={}, new details={}", id, addressDetails);

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Address not found with id={}", id);
                    return new RuntimeException("Address not found with id " + id);
                });

        address.setCity(addressDetails.getCity());
        address.setStreet(addressDetails.getStreet());
        address.setHouseNumber(addressDetails.getHouseNumber());
        address.setApartmentNumber(addressDetails.getApartmentNumber());

        if (Boolean.TRUE.equals(addressDetails.getDefault())) {
            logger.info("Updating default address for userId={}", address.getUserId());
            setDefaultAddress(address.getUserId(), id);
        }

        Address updatedAddress = addressRepository.save(address);
        logger.info("Address updated with id={}", updatedAddress.getId());
        return updatedAddress;
    }

    public void deleteAddress(Long id) {
        logger.info("Deleting address with id={}", id);
        addressRepository.deleteById(id);
        logger.info("Address deleted with id={}", id);
    }

    @Transactional
    public void setDefaultAddress(Long userId, Long addressId) {
        logger.info("Setting address with id={} as default for userId={}", addressId, userId);

        List<Address> userAddresses = addressRepository.findByUserId(userId);
        userAddresses.forEach(addr -> {
            if (addr.getDefault() != null && addr.getDefault()) {
                addr.setDefault(false);
                addressRepository.save(addr);
                logger.info("Removed default status from address with id={}", addr.getId());
            }
        });

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> {
                    logger.error("Address not found with id={}", addressId);
                    return new RuntimeException("Address not found with id " + addressId);
                });

        address.setDefault(true);
        addressRepository.save(address);
        logger.info("Address with id={} set as default", addressId);
    }

    public Optional<Address> getDefaultAddress(Long userId) {
        logger.info("Fetching default address for userId={}", userId);
        return addressRepository.findByUserIdAndIsDefaultTrue(userId);
    }
}

