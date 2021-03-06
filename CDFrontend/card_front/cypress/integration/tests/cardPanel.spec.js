/// <reference types="cypress" />
Cypress.Cookies.defaults({
  whitelist: 'Admin-Token'
});

context('CardPanel', () => {
  let LOCAL_STORAGE_MEMORY = {};

  Cypress.Commands.add("saveLocalStorage", () => {
    Object.keys(localStorage).forEach(key => {
      LOCAL_STORAGE_MEMORY[key] = localStorage[key];
    });
  });

  Cypress.Commands.add("restoreLocalStorage", () => {
    Object.keys(LOCAL_STORAGE_MEMORY).forEach(key => {
      localStorage.setItem(key, LOCAL_STORAGE_MEMORY[key]);
    });
  });

  beforeEach(() => {
    cy.restoreLocalStorage();
    cy.wait(2000);
  });

  afterEach(() => {
    cy.saveLocalStorage();
  });

  it('Login',  () => {
    cy.visit('http://localhost:8081/');
    //
    // cy.get('.username-input')
    //   .type('admin1');
    //
    // cy.get('.password-input')
    //   .type('111111');
    //
    // cy.get('.login-button').click();

    // cy.get('.hamburger').click();

    cy.get(':nth-child(3) > .el-submenu > .el-submenu__title > .el-submenu__icon-arrow').click();

    cy.get(':nth-child(3) > .el-submenu > .el-menu > :nth-child(3) > a > .el-menu-item > span').click();

  });

  it('Card Panel Invalid Form', () => {
    cy.get('.filter-container > .el-button').click();

    cy.get('.cancelOuterButton > span').click();

    cy.get('.filter-container > .el-button').click();

    cy.get(':nth-child(1) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(1) > .el-form-item__content > .el-input > .el-input__inner')
      .type('E2E Ultra Card');

    cy.get(':nth-child(2) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(2) > .el-form-item__content > .el-input > .el-input__inner')
      .type('E2E Super Rare');

    cy.get(':nth-child(3) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(3) > .el-form-item__content > .el-input > .el-input__inner')
      .type('999');

    cy.get(':nth-child(4) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(4) > .el-form-item__content > .el-input > .el-input__inner')
      .type('999');

    cy.get(':nth-child(5) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(5) > .el-form-item__content > .el-input > .el-input__inner')
      .type('999');

    cy.get(':nth-child(6) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(6) > .el-form-item__content > .el-input > .el-input__inner')
      .type('999');

    cy.get(':nth-child(7) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(7) > .el-form-item__content > .el-input > .el-input__inner')
      .type('999');

    cy.get(':nth-child(8) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(8) > .el-form-item__content > .el-input > .el-input__inner')
      .type('999');

    cy.get(':nth-child(9) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(9) > .el-form-item__content > .el-input > .el-input__inner')
      .type('2');

    cy.get('.confirmOuterButton > span').click({force: true});
  });

  it('Card Panel Update Data', () => {

    cy.get(':nth-child(3) > .el-table_1_column_2 > .cell > .link-type').click({force: true});

    cy.get(':nth-child(2) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(2) > .el-form-item__content > .el-input > .el-input__inner')
      .type('E2E Not Ultra Card');

    cy.get(':nth-child(3) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(3) > .el-form-item__content > .el-input > .el-input__inner')
      .type('E2E Super Not Rare');

    cy.get(':nth-child(4) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(4) > .el-form-item__content > .el-input > .el-input__inner')
      .type('111');

    cy.get(':nth-child(5) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(5) > .el-form-item__content > .el-input > .el-input__inner')
      .type('111');

    cy.get(':nth-child(6) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(6) > .el-form-item__content > .el-input > .el-input__inner')
      .type('111');

    cy.get(':nth-child(7) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(7) > .el-form-item__content > .el-input > .el-input__inner')
      .type('111');

    cy.get(':nth-child(8) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(8) > .el-form-item__content > .el-input > .el-input__inner')
      .type('111');

    cy.get(':nth-child(9) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(9) > .el-form-item__content > .el-input > .el-input__inner')
      .type('111');

    cy.get(':nth-child(10) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(10) > .el-form-item__content > .el-input > .el-input__inner')
      .type('3');

    cy.get('.confirmOuterButton > span').click({force: true});

  });

  it('Card Panel Delete Data', () => {

    cy.get(':nth-child(3) > .el-table_1_column_2 > .cell > .link-type').click({force: true});

    cy.get('.deleteOuterButton').click();

    cy.get('.el-dialog__body > .el-input > .el-input__inner')
      .type('222222');

    cy.get('.el-dialog__body > .el-button').click();

    cy.get('.cancelInnerButton').click();

    cy.get('.deleteOuterButton').click({force: true});

    cy.get('.el-dialog__body > .el-input > .el-input__inner')
      .type('111111');

    cy.get('.el-dialog__body > .el-button').click();

    cy.wait(500);
    cy.get('.deleteInnerButton').click({ multiple: true, force: true });
    cy.wait(500);

    cy.clearCookies()

  })


});
