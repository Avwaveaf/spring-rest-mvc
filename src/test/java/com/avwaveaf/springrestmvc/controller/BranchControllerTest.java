package com.avwaveaf.springrestmvc.controller;

import com.avwaveaf.springrestmvc.controller.exception.NotFoundException;
import com.avwaveaf.springrestmvc.model.branch.Branch;
import com.avwaveaf.springrestmvc.service.BranchService;
import com.avwaveaf.springrestmvc.service.BranchServiceImpl;
import com.avwaveaf.springrestmvc.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BranchController.class)
class BranchControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BranchService branchService;

    @Captor
    ArgumentCaptor<UUID> acUUID;
    @Captor
    ArgumentCaptor<Branch> acBranch;

    BranchServiceImpl branchServiceImpl = new BranchServiceImpl();

    @BeforeEach
    void setUp() {
        branchServiceImpl = new BranchServiceImpl();
    }

    @Test
    void getBranchById() throws Exception {
        /// Given
        Branch testBranch = branchServiceImpl.listBranches().get(0);
        given(branchService.getBranchById(testBranch.getId())).willReturn(Optional.of(testBranch));

        /// When
        mockMvc.perform(get(BranchController.BRANCH_BASE_URL + testBranch.getId()).accept(MediaType.APPLICATION_JSON))
                /// Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBranch.getId().toString())))
                .andExpect(jsonPath("$.branchName", is(testBranch.getBranchName())));
    }

    @Test
    void getBranches() throws Exception {
        /// Given
        given(branchService.listBranches()).willReturn(branchServiceImpl.listBranches());
        int listSize = branchServiceImpl.listBranches().size();

        /// When
        mockMvc.perform(get(BranchController.BRANCH_BASE_URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(listSize)));
    }

    @Test
    void createNewBranch() throws Exception {
        /// Given
        Branch branch = branchServiceImpl.listBranches().get(0);
        branch.setVersion(null);
        branch.setUpdatedDate(null);
        branch.setCreatedDate(null);

        given(branchService.saveNewBranch(any(Branch.class))).willReturn(branchServiceImpl.listBranches().get(1));

        /// When
        mockMvc.perform(post(BranchController.BRANCH_BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branch))
                )
                ///  Then
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateBranch() throws Exception {
        /// Given
        Branch branch = branchServiceImpl.listBranches().get(0);

        /// When
        mockMvc.perform(put(BranchController.BRANCH_BASE_URL + branch.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branch))
                )
                /// Then
                .andExpect(status().isNoContent());
        /// Then
        verify(branchService).updateById(any(UUID.class), any(Branch.class));
    }

    @Test
    void deleteBranch() throws Exception {
        /// Given
        Branch branch = branchServiceImpl.listBranches().get(0);

        /// When
        mockMvc.perform(delete(BranchController.BRANCH_BASE_URL + branch.getId())
                        .accept(MediaType.APPLICATION_JSON)
                )
                /// Then
                .andExpect(status().isNoContent());

        /// Then
        verify(branchService).deleteBranchById(acUUID.capture());
        assertThat(branch.getId()).isEqualTo(acUUID.getValue());
    }

    @Test
    void patchBranch() throws Exception {
        /// Given
        Branch branch = branchServiceImpl.listBranches().get(0);
        Map<String, Object> branchMap = new HashMap<>();
        branchMap.put("branchName", "New Branch Name");

        /// When
        mockMvc.perform(patch(BranchController.BRANCH_BASE_URL + branch.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branchMap))
                )
                .andExpect(status().isNoContent());

        /// Then
        verify(branchService).patchBranchById(acUUID.capture(), acBranch.capture());
        assertThat(branchMap.get("branchName")).isEqualTo(acBranch.getValue().getBranchName());
    }

    /// =========== EXCEPTION ===========
    @Test
    void branchByIdNotFound() throws Exception {
        /// Given
        given(branchService.getBranchById(any(UUID.class))).willThrow(NotFoundException.class);

        /// When
        mockMvc.perform(get(BranchController.BRANCH_BASE_URL + UUID.randomUUID()))
                /// Then
                .andExpect(status().isNotFound());
    }

}