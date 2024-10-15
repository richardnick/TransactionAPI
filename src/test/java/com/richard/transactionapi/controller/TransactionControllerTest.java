//package com.richard.transactionapi.controller;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TransactionControllerTest {
//
//    @Autowired
//    private TransactionController transactionController;
//
//    @Autowired
//    private TransactionService transactionService;
//
//    @MockBean
//    private TransactionService mockTransactionService;
//
//    @Test
//    public void testGetTransactions() throws Exception {
//        // given
//        String status = "pending";
//        String accountNumber = "1234567890";
//        String startDate = "2022-01-01T00:00:00";
//        String endDate = "2022-01-31T23:59:59";
//
//        // mock the transaction service
//        List<Transaction> transactions = new ArrayList<>();
//        transactions.add(new Transaction());
//        when(mockTransactionService.getTransactions(status, accountNumber, startDate, endDate)).thenReturn(transactions);
//
//        // when
//        MvcResult result = mockMvc.perform(get("/transactions")
//                        .param("status", status)
//                        .param("accountNumber", accountNumber)
//                        .param("startDate", startDate)
//                        .param("endDate", endDate))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(jsonPath("$.length()").value(1))
//                .andReturn();
//
//        // then
//        String responseContent = result.getResponse().getContentAsString();
//        List<Transaction> responseTransactions = new ObjectMapper().readValue(responseContent, new TypeReference<List<Transaction>>() {});
//        assertEquals(transactions, responseTransactions);
//    }
//
//    @Test
//    public void testProcessTransferRequest() throws Exception {
//        // given
//        TransferRequest request = new TransferRequest();
//        request.setFromAccountNumber("1234567890");
//        request.setToAccountNumber("9876543210");
//        request.setAmount(100.0);
//        request.setDescription("Test transfer");
//
//        // mock the transaction service
//        TransferResponse response = new TransferResponse();
//        response.setTransactionReference("1234567890");
//        response.setStatus("pending");
//        response.setStatusMessage("Transaction is being processed");
//        when(mockTransactionService.processTransferRequest(request)).thenReturn(response);
//
//        // when
//        MvcResult result = mockMvc.perform(post("/transfer")
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .content(new ObjectMapper().writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(jsonPath("$.transactionReference").value("1234567890"))
//                .andExpect(jsonPath("$.status").value("pending"))
//                .andExpect(jsonPath("$.statusMessage").value("Transaction is being processed"))
//                .andReturn();
//
//        // then
//        String responseContent = result.getResponse().getContentAsString();
//        TransferResponse
//
//
//
//
