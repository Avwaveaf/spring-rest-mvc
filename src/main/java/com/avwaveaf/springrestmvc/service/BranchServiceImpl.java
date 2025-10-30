package com.avwaveaf.springrestmvc.service;

import com.avwaveaf.springrestmvc.model.branch.Branch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class BranchServiceImpl implements BranchService {

    private Map<UUID, Branch> branchMap;

    {
        // Initialize with 15 different branches
        branchMap = new HashMap<>();

        Branch b1 = Branch.builder()
                .id(UUID.randomUUID())
                .branchName("Central City Branch")
                .address("100 Main St, Central City")
                .phoneNumber("+1-555-0100")
                .email("central@company.com")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Branch b2 = Branch.builder()
                .id(UUID.randomUUID())
                .branchName("Northside Branch")
                .address("200 North Ave, Metropolis")
                .phoneNumber("+1-555-0101")
                .version(1)
                .email("north@company.com")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Branch b3 = Branch.builder()
                .id(UUID.randomUUID())
                .branchName("South Park Branch")
                .address("300 South Rd, Metropolis")
                .phoneNumber("+1-555-0102")
                .email("south@company.com")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Branch b4 = Branch.builder()
                .id(UUID.randomUUID())
                .branchName("Eastwood Branch")
                .address("400 East Blvd, Star City")
                .phoneNumber("+1-555-0103")
                .email("east@company.com")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Branch b5 = Branch.builder()
                .id(UUID.randomUUID())
                .branchName("Westend Branch")
                .address("500 West St, Star City")
                .phoneNumber("+1-555-0104")
                .version(1)
                .email("west@company.com")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Branch b6 = Branch.builder()
                .id(UUID.randomUUID())
                .branchName("Riverside Branch")
                .address("600 River Ln, Gotham")
                .phoneNumber("+1-555-0105")
                .email("riverside@company.com")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Branch b7 = Branch.builder()
                .id(UUID.randomUUID())
                .branchName("Hillside Branch")
                .address("700 Hill Rd, Gotham")
                .phoneNumber("+1-555-0106")
                .version(1)
                .email("hillside@company.com")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Branch b8 = Branch.builder()
                .id(UUID.randomUUID())
                .branchName("Lakeshore Branch")
                .address("800 Lake Dr, Coast City")
                .phoneNumber("+1-555-0107")
                .version(1)
                .email("lakeshore@company.com")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Branch b9 = Branch.builder()
                .id(UUID.randomUUID())
                .branchName("Uptown Branch")
                .address("900 Uptown Ave, Coast City")
                .version(1)
                .phoneNumber("+1-555-0108")
                .email("uptown@company.com")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Branch b10 = Branch.builder()
                .id(UUID.randomUUID())
                .branchName("Downtown Branch")
                .address("1000 Downtown St, Central City")
                .phoneNumber("+1-555-0109")
                .email("downtown@company.com")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Branch b11 = Branch.builder()
                .id(UUID.randomUUID())
                .branchName("Harbor Branch")
                .address("1100 Harbor Way, Blüdhaven")
                .phoneNumber("+1-555-0110")
                .email("harbor@company.com")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Branch b12 = Branch.builder()
                .id(UUID.randomUUID())
                .branchName("Airport Branch")
                .address("1200 Airport Rd, Metropolis")
                .phoneNumber("+1-555-0111")
                .version(1)
                .email("airport@company.com")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Branch b13 = Branch.builder()
                .id(UUID.randomUUID())
                .branchName("University Branch")
                .address("1300 Campus Dr, Star City")
                .phoneNumber("+1-555-0112")
                .version(1)
                .email("university@company.com")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Branch b14 = Branch.builder()
                .id(UUID.randomUUID())
                .branchName("Industrial Park Branch")
                .address("1400 Industry Blvd, Gotham")
                .phoneNumber("+1-555-0113")
                .email("industrial@company.com")
                .createdDate(LocalDateTime.now())
                .version(1)
                .updatedDate(LocalDateTime.now())
                .build();

        Branch b15 = Branch.builder()
                .id(UUID.randomUUID())
                .branchName("Old Town Branch")
                .address("1500 Old Town Rd, Coast City")
                .phoneNumber("+1-555-0114")
                .version(1)
                .email("oldtown@company.com")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        branchMap.put(b1.getId(), b1);
        branchMap.put(b2.getId(), b2);
        branchMap.put(b3.getId(), b3);
        branchMap.put(b4.getId(), b4);
        branchMap.put(b5.getId(), b5);
        branchMap.put(b6.getId(), b6);
        branchMap.put(b7.getId(), b7);
        branchMap.put(b8.getId(), b8);
        branchMap.put(b9.getId(), b9);
        branchMap.put(b10.getId(), b10);
        branchMap.put(b11.getId(), b11);
        branchMap.put(b12.getId(), b12);
        branchMap.put(b13.getId(), b13);
        branchMap.put(b14.getId(), b14);
        branchMap.put(b15.getId(), b15);
    }

    @Override
    public List<Branch> listBranches() {
        log.debug("Requested list of branches (Service)");
        return new ArrayList<>(branchMap.values());
    }

    @Override
    public Branch getBranchById(UUID id) {
        log.debug("Requested branch by id (Service): {}", id);
        return branchMap.get(id);
    }

    @Override
    public Branch saveNewBranch(Branch branch) {
        Branch saved = Branch.builder()
                .id(UUID.randomUUID())
                .branchName(branch.getBranchName())
                .address(branch.getAddress())
                .phoneNumber(branch.getPhoneNumber())
                .email(branch.getEmail())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        branchMap.put(saved.getId(), saved);
        return saved;
    }

    @Override
    public void updateById(UUID id, Branch branch) {
        Branch existing = branchMap.get(id);
        if (existing == null) {
            return;
        }
        existing.setBranchName(branch.getBranchName());
        existing.setAddress(branch.getAddress());
        existing.setPhoneNumber(branch.getPhoneNumber());
        existing.setEmail(branch.getEmail());
        existing.setUpdatedDate(LocalDateTime.now());
        branchMap.put(id, existing);
    }

    @Override
    public void deleteBranchById(UUID id) {
        branchMap.remove(id);
    }
}
