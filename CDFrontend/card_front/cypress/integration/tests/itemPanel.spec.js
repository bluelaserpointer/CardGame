/// <reference types="cypress" />


context('ItemPanel', () => {
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
    cy.visit('http://localhost:9527/#/login?redirect=%2Fdashboard');

    cy.get('.username-input')
      .type('admin1');

    cy.get('.password-input')
      .type('111111');

    cy.get('.login-button').click();

    // cy.get('.hamburger').click();

    cy.get(':nth-child(3) > .el-submenu > .el-submenu__title > .el-submenu__icon-arrow').click();

    cy.get(':nth-child(3) > .el-submenu > .el-menu > :nth-child(2) > a > .el-menu-item > span').click();

  });

  it('Item Panel Invalid Form', () => {
    cy.get('.filter-container > .el-button').click();

    cy.get('.cancelOuterButton > span').click();

    cy.get('.filter-container > .el-button').click();

    cy.get(':nth-child(1) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(1) > .el-form-item__content > .el-input > .el-input__inner')
      .type('E2E Ultra Item');

    cy.get(':nth-child(2) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(2) > .el-form-item__content > .el-input > .el-input__inner')
      .type('999');

    cy.get('.confirmOuterButton > span').click({force: true});
  });

  it('Item Panel Update Data', () => {
    cy.get(':nth-child(3) > .el-table_1_column_2 > .cell > .link-type').click({force: true});

    cy.get(':nth-child(2) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(2) > .el-form-item__content > .el-input > .el-input__inner')
      .type('E2E Not Ultra Item');

    cy.get(':nth-child(3) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(3) > .el-form-item__content > .el-input > .el-input__inner')
      .type('111');

    cy.get('.confirmOuterButton > span').click({force: true});
  });

  it('Item Panel Delete Data', () => {
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

    cy.get('.deleteInnerButton').click({force: true});

  })
});
