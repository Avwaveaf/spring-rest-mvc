package com.avwaveaf.springrestmvc.controller;

import com.avwaveaf.springrestmvc.model.branch.Branch;
import com.avwaveaf.springrestmvc.service.BranchService;
import com.avwaveaf.springrestmvc.service.BranchServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BranchController.class)
class BranchControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BranchService branchService;

    BranchServiceImpl branchServiceImpl = new BranchServiceImpl();

    @Test
    void getBranchById() throws Exception {
        /// Given
        Branch testBranch = branchServiceImpl.listBranches().get(0);
        given(branchService.getBranchById(testBranch.getId())).willReturn(testBranch);

        /// When
        mockMvc.perform(get("/branch/" + testBranch.getId()).accept(MediaType.APPLICATION_JSON))
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
        mockMvc.perform(get("/branch").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(listSize)));
    }

}