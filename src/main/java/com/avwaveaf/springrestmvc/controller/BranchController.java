package com.avwaveaf.springrestmvc.controller;

import com.avwaveaf.springrestmvc.controller.exception.NotFoundException;
import com.avwaveaf.springrestmvc.model.branch.Branch;
import com.avwaveaf.springrestmvc.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BranchController {
    public static final String BRANCH_BASE_URL = "/branch/";
    public static final String BRANCH_ID_URL = "/branch/{branchId}";
    private final BranchService branchService;

    @GetMapping(BRANCH_BASE_URL)
    public List<Branch> getListBranches() {
        return branchService.listBranches();
    }

    @GetMapping(BRANCH_ID_URL)
    public Branch getBranchById(@PathVariable UUID branchId) {
        return branchService.getBranchById(branchId).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping(BRANCH_ID_URL)
    public ResponseEntity deleteById(@PathVariable UUID branchId) {
        branchService.deleteBranchById(branchId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(BRANCH_BASE_URL)
    public ResponseEntity saveNewBranch(@RequestBody Branch branch) {
        Branch saved = branchService.saveNewBranch(branch);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/branch/" + saved.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(BRANCH_ID_URL)
    public ResponseEntity putUpdateById(
            @PathVariable UUID branchId,
            @RequestBody Branch branch
    ) {
        branchService.updateById(branchId, branch);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/branch/" + branchId.toString());

        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BRANCH_ID_URL)
    public ResponseEntity patchUpdateById(
            @PathVariable UUID branchId,
            @RequestBody Branch branch
    ) {
        branchService.patchBranchById(branchId, branch);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
