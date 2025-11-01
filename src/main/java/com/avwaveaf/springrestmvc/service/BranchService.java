package com.avwaveaf.springrestmvc.service;

import com.avwaveaf.springrestmvc.model.branch.Branch;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BranchService {

    List<Branch> listBranches();
    Optional<Branch> getBranchById(UUID id);

    Branch saveNewBranch(Branch branch);

    void updateById(UUID id, Branch branch);

    void deleteBranchById(UUID id);

    void patchBranchById(UUID id, Branch branch);
}
