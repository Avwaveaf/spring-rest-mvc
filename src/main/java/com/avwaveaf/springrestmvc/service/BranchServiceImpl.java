package com.avwaveaf.springrestmvc.service;

import com.avwaveaf.springrestmvc.model.branch.Branch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BranchServiceImpl implements BranchService {
    @Override
    public List<Branch> listBranches() {
        return List.of();
    }

    @Override
    public Branch getBranchById(UUID id) {
        return null;
    }

    @Override
    public Branch saveNewBranch(Branch branch) {
        return null;
    }

    @Override
    public void updateById(UUID id, Branch branch) {

    }

    @Override
    public void deleteBranchById(UUID id) {

    }
}
