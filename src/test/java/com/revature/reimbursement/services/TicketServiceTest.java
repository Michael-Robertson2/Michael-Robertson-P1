package com.revature.reimbursement.services;

import com.revature.reimbursement.daos.TicketDao;
import com.revature.reimbursement.models.Ticket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class TicketServiceTest {

    private TicketService sut;
    private final TicketDao mockTicketDao = Mockito.mock(TicketDao.class);

    @Before
    public void init() {
        sut = new TicketService(mockTicketDao);
    }

    @Test
    public void test_isValidAmount_givenValidAmount() {
        double amount = 50;

        boolean condition = sut.isValidAmount(amount);

        assertTrue(condition);
    }

    @Test
    public void test_isValidAmount_givenInvalidAmounts() {
        double amount1 = 0;
        double amount2 = 10000;

        boolean condition1 = sut.isValidAmount(amount1);
        boolean condition2 = sut.isValidAmount(amount2);

        assertFalse(condition1);
        assertFalse(condition2);
    }

    @Test
    public void test_isValidDescription_givenValidDescription() {
        String desc = "Meal expenses";

        boolean condition = sut.isValidDescription(desc);

        assertTrue(condition);
    }

    @Test
    public void test_isValidDescription_givenInvalidDescriptions() {
        String desc1 = "";
        String desc2 = null;

        boolean condition1 = sut.isValidDescription(desc1);
        boolean condition2 = sut.isValidDescription(desc2);

        assertFalse(condition1);
        assertFalse(condition2);
    }

    @Test
    public void test_isValidType_givenValidType() {
        String type = "f05813a6-71b0-11ed-a1eb-0242ac120002";

        boolean condition = sut.isValidType(type);

        assertTrue(condition);
    }

    @Test
    public void test_isValidType_givenInvalidType() {
        String type = null;

        boolean condition = sut.isValidType(type);

        assertFalse(condition);
    }

    @Test
    public void test_isValidTicket_givenValidTicket() {
        Ticket ticket = new Ticket();

        boolean condition = sut.isValidTicket(ticket);

        assertTrue(condition);
    }

    @Test
    public void test_isValidTicket_givenInvalidTicket() {
        Ticket ticket = null;

        boolean condition = sut.isValidTicket(ticket);

        assertFalse(condition);
    }

    @Test
    public void test_isPending_givenPending() {
        String id = "ca684fbc-71ba-11ed-a1eb-0242ac120002";
        String input = "ca684fbc-71ba-11ed-a1eb-0242ac120002";
        TicketService spySut = Mockito.spy(sut);

        Mockito.when(mockTicketDao.getStatusIdByName("PENDING")).thenReturn(id);

        boolean condition = spySut.isPending(input);

        assertTrue(condition);
    }

    @Test
    public void test_isPending_givenNotPending() {
        String id = "ca684fbc-71ba-11ed-a1eb-0242ac120002";
        String input = "ca684fbc-71ba-11ed-a1eb-0242ac120004";
        TicketService spySut = Mockito.spy(sut);

        Mockito.when(mockTicketDao.getStatusIdByName("PENDING")).thenReturn(id);

        boolean condition = spySut.isPending(input);

        assertFalse(condition);
    }

    @Test
    public void test_isValidStatus_givenValidStatus() {
        String status = "ca684fbc-71ba-11ed-a1eb-0242ac120002";

        boolean condition = sut.isValidStatus(status);

        assertTrue(condition);
    }

    @Test
    public void test_isValidStatus_givenInvalidStatus() {
        String status = null;

        boolean condition = sut.isValidStatus(status);

        assertFalse(condition);
    }

    @Test
    public void test_getTypeIdByName() {
        String type = "f05813a6-71b0-11ed-a1eb-0242ac120002";
        TicketService spySut = Mockito.spy(sut);

        Mockito.when(mockTicketDao.getTypeIdByName("LODGING")).thenReturn(type);

        String output = spySut.getTypeIdByName("LODGING");

        assertEquals(type, output);
    }

    @Test
    public void test_getTicketById() {
        Ticket ticket = new Ticket();
        String id = "07d8561e-a0ef-40d3-8826-791ef797d1bf";
        TicketService spySut = Mockito.spy(sut);

        Mockito.when(mockTicketDao.findById(id)).thenReturn(ticket);

        Ticket ticket1 = spySut.getTicketById(id);

        assertEquals(ticket1, ticket);
    }

    @Test
    public void test_getStatusByName() {
        String status = "ca684fbc-71ba-11ed-a1eb-0242ac120004";
        TicketService spySut = Mockito.spy(sut);

        Mockito.when(mockTicketDao.getTypeIdByName("LODGING")).thenReturn(status);

        String output = spySut.getTypeIdByName("LODGING");

        assertEquals(status, output);
    }
}