/// <reference types="cypress" />
Cypress.Cookies.defaults({
  whitelist: 'Admin-Token'
});

context('PhasePanel', () => {
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

    cy.get('.username-input')
      .type('admin1');

    cy.get('.password-input')
      .type('111111');

    cy.get('.login-button').click();

    // cy.get('#hamburger-container').click();

    cy.get(':nth-child(3) > .el-submenu > .el-submenu__title').click();

    cy.get(':nth-child(3) > .el-submenu > .el-menu > :nth-child(1) > a > .el-menu-item').click();
  });

  it('Phase Panel Invalid Form', () => {
    for(let i = 0; i < 2; i++) {
      cy.get('.filter-container > .el-button').click();

      cy.get('.cancelOuterButton > span').click();

      cy.get('.filter-container > .el-button').click();

      cy.get('.confirmOuterButton > span').click();

      cy.get(':nth-child(1) > .el-form-item__content > .el-input > .el-input__inner')
        .type('10');

      cy.get('.confirmOuterButton > span').click();

      cy.get(':nth-child(2) > .el-form-item__content > .el-input > .el-input__inner')
        .type('1');

      cy.get('.confirmOuterButton > span').click();
    }
  });

  it('Phase Panel Update Data', () => {
    cy.get(':nth-child(7) > .el-table_1_column_1 > .cell > .link-type').click();

    cy.get(':nth-child(2) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(2) > .el-form-item__content > .el-input > .el-input__inner')
      .type('15');

    cy.get(':nth-child(3) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get('.confirmOuterButton > span').click();

    cy.get(':nth-child(3) > .el-form-item__content > .el-input > .el-input__inner')
      .type('2');

    cy.get('.confirmOuterButton > span').click();
  });

  it('Phase Panel Delete Data', () => {
    cy.get(':nth-child(7) > .el-table_1_column_1 > .cell > .link-type').click();

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
  });

  it('Phase Panel Edit Choose ChapterPhase Normal', () => {
    // cy.get('#tab-second').click();
    //
    // // Choosing Chapter
    // cy.get('.chapterSelectArea > .el-select > .el-input > .el-input__inner')
    //   .click();
    // cy.get('.el-select-group > :nth-child(1)').click();
    //
    // // Choosing Phase
    // cy.get('.phaseSelectArea > .el-select > .el-input > .el-input__inner')
    //   .click();
    // cy.get('[style="min-width: 217px; position: absolute; top: 190px; left: 589px; transform-origin: center top; z-index: 2005;"] > .el-scrollbar > .el-select-dropdown__wrap > .el-scrollbar__view > .el-select-group__wrap > :nth-child(2) > .el-select-group > :nth-child(1)').click();
    //
    // // cy.get('.el-select-group__wrap > :nth-child(2) > .el-select-group > :nth-child(1) > .phaseSelectionOption')
    // //   .click({force: true});
  });
  //
  // it('Phase Panel Edit PlaceCard Testing', () => {
  //   // Choose a card, and place at position-6
  //   cy.get('tbody > :nth-child(1) > .el-table_8_column_18').click();
  //   cy.get(':nth-child(6) > .el-button').click();
  //
  //
  //   // CASE-1   Restore
  //   // Choose another phase without confirming or restoring
  //   cy.get('[style="margin-right: auto;"] > .el-select > .el-input > .el-input__inner')
  //     .click();
  //   cy.get('[style="min-width: 217px; transform-origin: center top; z-index: 2015; position: absolute; top: 206px; left: 589px;"] > .el-scrollbar > .el-select-dropdown__wrap > .el-scrollbar__view > .el-select-group__wrap > :nth-child(2) > .el-select-group > :nth-child(2)')
  //     .click();
  //   // Restoring
  //   cy.get('.restoreButton').click();
  //   // Choose another phase after restoring
  //   cy.get('[style="margin-right: auto;"] > .el-select > .el-input > .el-input__inner')
  //     .click();
  //   cy.get('[style="min-width: 217px; transform-origin: center top; z-index: 2015; position: absolute; top: 206px; left: 589px;"] > .el-scrollbar > .el-select-dropdown__wrap > .el-scrollbar__view > .el-select-group__wrap > :nth-child(2) > .el-select-group > :nth-child(2)')
  //     .click();
  //
  //   // CASE-2   Confirm
  //   // Choosing Phase
  //   cy.get('[style="margin-right: auto;"] > .el-select > .el-input > .el-input__inner')
  //     .click();
  //   cy.get('[style="min-width: 217px; transform-origin: center top; z-index: 2015; position: absolute; top: 206px; left: 589px;"] > .el-scrollbar > .el-select-dropdown__wrap > .el-scrollbar__view > .el-select-group__wrap > :nth-child(2) > .el-select-group > :nth-child(1)')
  //     .click();
  //   // Choose a card, and place at position-6
  //   cy.get('tbody > :nth-child(1) > .el-table_8_column_18').click();
  //   cy.get(':nth-child(6) > .el-button').click();
  //   // Choose another phase without confirming or restoring
  //   cy.get('[style="margin-right: auto;"] > .el-select > .el-input > .el-input__inner')
  //     .click();
  //   cy.get('[style="min-width: 217px; transform-origin: center top; z-index: 2015; position: absolute; top: 206px; left: 589px;"] > .el-scrollbar > .el-select-dropdown__wrap > .el-scrollbar__view > .el-select-group__wrap > :nth-child(2) > .el-select-group > :nth-child(2)')
  //     .click();
  //   // Confirming
  //   cy.get('.confirmButton').click();
  //
  // })

});
