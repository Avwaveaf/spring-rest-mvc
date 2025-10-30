package com.avwaveaf.springrestmvc.controller;

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
    private final BranchService branchService;

    @GetMapping("/branch")
    public List<Branch> getListBranches() {
        return branchService.listBranches();
    }

    @GetMapping("/branch/{id}")
    public Branch getBranchById(@PathVariable UUID id) {
        return branchService.getBranchById(id);
    }

    @GetMapping("/branch/delete/{id}")
    public ResponseEntity deleteById(@PathVariable UUID id) {
        branchService.deleteBranchById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/branch")
    public ResponseEntity saveNewBranch(@RequestBody Branch branch) {
        Branch saved = branchService.saveNewBranch(branch);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/branch/" + saved.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/branch/{id}")
    public ResponseEntity putUpdateById(
            @PathVariable UUID id,
            @RequestBody Branch branch
    ) {
        branchService.updateById(id, branch);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/branch/" + id.toString());

        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

}
