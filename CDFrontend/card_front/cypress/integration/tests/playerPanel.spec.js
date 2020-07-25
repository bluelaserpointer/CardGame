/// <reference types="cypress" />


context('UserPanel', () => {
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

    cy.get(':nth-child(3) > .el-submenu > .el-submenu__title > .el-submenu__icon-arrow').click();

    cy.get(':nth-child(3) > .el-submenu > .el-menu > :nth-child(4) > a > .el-menu-item > span').click();

  });

  it('User Panel Invalid Form', () => {
    cy.get('#pane-first > .app-container > .filter-container > .el-button').click();

    cy.get(':nth-child(3) > .outerDialog > .cancelOuterButton').click();

    cy.get('#pane-first > .app-container > .filter-container > .el-button').click();

    cy.get(':nth-child(1) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(1) > .el-form-item__content > .el-input > .el-input__inner')
      .type('E2E Ultra User');

    cy.get(':nth-child(2) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(2) > .el-form-item__content > .el-input > .el-input__inner')
      .type('E2E Super Email');

    cy.get(':nth-child(3) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(3) > .el-form-item__content > .el-input > .el-input__inner')
      .type('Password');

    cy.get(':nth-child(4) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(4) > .el-form-item__content > .el-input > .el-input__inner')
      .type('123456');

    cy.get(':nth-child(5) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(5) > .el-form-item__content > .el-input > .el-input__inner')
      .type('100');

    cy.get(':nth-child(6) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(6) > .el-form-item__content > .el-input > .el-input__inner')
      .type('100');

    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click({force: true});
  });

  it('User Panel Update Data', () => {

    cy.get(':nth-child(3) > .el-table_1_column_2 > .cell > .link-type').click({force: true});

    cy.get(':nth-child(2) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(2) > .el-form-item__content > .el-input > .el-input__inner')
      .type('E2E Not Ultra User');

    cy.get(':nth-child(3) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(3) > .el-form-item__content > .el-input > .el-input__inner')
      .type('E2E Not Super Email');

    cy.get(':nth-child(4) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(4) > .el-form-item__content > .el-input > .el-input__inner')
      .type('Password');

    cy.get(':nth-child(5) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(5) > .el-form-item__content > .el-input > .el-input__inner')
      .type('123456');

    cy.get(':nth-child(6) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(6) > .el-form-item__content > .el-input > .el-input__inner')
      .type('100');

    cy.get(':nth-child(7) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();

    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(7) > .el-form-item__content > .el-input > .el-input__inner')
      .type('100');

    cy.get(':nth-child(9) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();
    cy.get(':nth-child(10) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();
    cy.get(':nth-child(11) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();
    cy.get(':nth-child(12) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();
    cy.get(':nth-child(13) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();
    cy.get(':nth-child(14) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();
    cy.get(':nth-child(15) > .el-form-item__content > .el-input > .el-input__inner')
      .clear();


    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(9) > .el-form-item__content > .el-input > .el-input__inner')
      .type('100');



    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(10) > .el-form-item__content > .el-input > .el-input__inner')
      .type('100');



    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(11) > .el-form-item__content > .el-input > .el-input__inner')
      .type('100');


    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(12) > .el-form-item__content > .el-input > .el-input__inner')
      .type('100');



    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(13) > .el-form-item__content > .el-input > .el-input__inner')
      .type('100');



    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(14) > .el-form-item__content > .el-input > .el-input__inner')
      .type('100');



    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click();

    cy.get(':nth-child(15) > .el-form-item__content > .el-input > .el-input__inner')
      .type('100');

    cy.get(':nth-child(3) > .outerDialog > .confirmOuterButton > span').click({force: true});

  });

  it('User Panel Delete Data', () => {
    cy.get(':nth-child(3) > .el-table_1_column_2 > .cell > .link-type').click({force: true});

    cy.get(':nth-child(3) > .outerDialog > .deleteOuterButton').click({force: true});

    cy.get('.el-dialog__body > .el-input > .el-input__inner')
      .type('222222');

    cy.get('.el-dialog__body > .el-button').click();

    cy.get('.deleteInnerButton').click({ multiple: true, force: true });

    cy.get('.cancelInnerButton').click({ multiple: true, force: true });

    cy.get('.deleteOuterButton').click({force: true});

    cy.get('.el-dialog__body > .el-input > .el-input__inner')
      .type('111111');

    cy.get('.el-dialog__body > .el-button').click();

    cy.get('.deleteInnerButton').click({ multiple: true, force: true });
  });

  it('User Panel OwnItem Invalid Form', () => {

    cy.get('#tab-second').click();

    cy.get('#pane-second > .app-container > .filter-container > .el-button').click();

    cy.get(':nth-child(3) > .outerDialog > .cancelOwnItemOuterButton').click();

    cy.get('#pane-second > .app-container > .filter-container > .el-button').click();

    cy.get('.userIdOwnItemInput > .el-input__inner').type('1');

    cy.get(':nth-child(3) > .outerDialog > .confirmOwnItemOuterButton').click();

    cy.get('.itemIdOwnItemInput > .el-input__inner').type('1');

    cy.get(':nth-child(3) > .outerDialog > .confirmOwnItemOuterButton').click();

    cy.get('.itemCountOwnItemInput > .el-input__inner').type('1');

    cy.get(':nth-child(3) > .outerDialog > .confirmOwnItemOuterButton').click();
  });

  it('User Panel OwnItem Update Data', () => {
    cy.get(':nth-child(2) > .el-table_2_column_16 > .cell > .link-type').click();

    cy.get('.userIdOwnItemInput > .el-input__inner').clear();

    cy.get(':nth-child(3) > .outerDialog > .confirmOwnItemOuterButton').click();

    cy.get('.itemIdOwnItemInput > .el-input__inner').clear();

    cy.get(':nth-child(3) > .outerDialog > .confirmOwnItemOuterButton').click();

    cy.get('.itemCountOwnItemInput > .el-input__inner').clear();

    cy.get(':nth-child(3) > .outerDialog > .confirmOwnItemOuterButton').click();

    cy.get('.el-date-editor > .el-input__inner').click({multiple: true, force: true});

    cy.get('.el-picker-panel__footer > .el-button--text > span').click({multiple: true, force: true});

    cy.get('.userIdOwnItemInput > .el-input__inner').type('1');

    cy.get('.itemIdOwnItemInput > .el-input__inner').type('1');

    cy.get('.itemCountOwnItemInput > .el-input__inner').type('1');

    cy.get(':nth-child(3) > .outerDialog > .confirmOwnItemOuterButton').click();
  });

  it('User Panel OwnItem Delete Data', () => {
    cy.get(':nth-child(2) > .el-table_2_column_16 > .cell > .link-type').click();

    cy.get(':nth-child(3) > .outerDialog > .deleteOwnItemOuterButton').click({force: true});

    cy.get('.confirmOwnItemInput .el-input__inner').type('222222');

    cy.get('.confirmOwnItemInnerButton').click();

    cy.get('.deleteOwnItemInnerButton').click({ multiple: true, force: true });

    cy.get('.cancelOwnItemInnerButton').click({ multiple: true, force: true });

    cy.get('.deleteOwnItemOuterButton').click({force: true});

    cy.get('.confirmOwnItemInput > .el-input__inner')
      .type('111111');

    cy.get('.confirmOwnItemInnerButton').click();

    cy.get('.deleteOwnItemInnerButton').click({ multiple: true, force: true });
  });

  it('User Panel OwnCard Invalid Form', () => {

    cy.get('#tab-third').click();

    cy.get('#pane-third > .app-container > .filter-container > .el-button').click();

    cy.get('.cancelOwnCardOuterButton').click();

    cy.get('#pane-third > .app-container > .filter-container > .el-button').click();

    cy.get('.userIdOwnCardInput > .el-input__inner').type('1');

    cy.get('.confirmOwnCardOuterButton').click();

    cy.get('.cardIdOwnCardInput > .el-input__inner').type('2');

    cy.get('.confirmOwnCardOuterButton').click();
  });

  it('User Panel OwnCard Update Data', () => {
    cy.get(':nth-child(2) > .el-table_3_column_21 > .cell > .link-type').click();

    cy.get('.userIdOwnCardInput > .el-input__inner').clear();
    cy.get('.cardIdOwnCardInput > .el-input__inner').clear();
    cy.get('.cardLevelOwnCardInput > .el-input__inner').clear();
    cy.get('.cardCurExpOwnCardInput > .el-input__inner').clear();
    cy.get('.cardLevelLimitOwnCardInput > .el-input__inner').clear();
    cy.get('.repetitiveOwnsOwnCardInput > .el-input__inner').clear();

    cy.get('.userIdOwnCardInput > .el-input__inner').type('1');
    cy.get(':nth-child(3) > .outerDialog > .confirmOwnCardOuterButton').click();

    cy.get('.cardIdOwnCardInput > .el-input__inner').type('2');
    cy.get(':nth-child(3) > .outerDialog > .confirmOwnCardOuterButton').click();

    cy.get('.cardLevelOwnCardInput > .el-input__inner').type('1');
    cy.get(':nth-child(3) > .outerDialog > .confirmOwnCardOuterButton').click();

    cy.get('.cardCurExpOwnCardInput > .el-input__inner').type('1');
    cy.get(':nth-child(3) > .outerDialog > .confirmOwnCardOuterButton').click();

    cy.get('.cardLevelLimitOwnCardInput > .el-input__inner').type('1');
    cy.get(':nth-child(3) > .outerDialog > .confirmOwnCardOuterButton').click();

    cy.get('.repetitiveOwnsOwnCardInput > .el-input__inner').type('1');

    cy.get('.el-date-editor > .el-input__inner').click({multiple: true, force: true});

    cy.get('.el-picker-panel__footer > .el-button--text > span').click({multiple: true, force: true});

    cy.get(':nth-child(3) > .outerDialog > .confirmOwnCardOuterButton').click();
  });

  it('User Panel OwnCard Delete Data', () => {
    cy.get(':nth-child(2) > .el-table_3_column_21 > .cell > .link-type').click();

    cy.get(':nth-child(3) > .outerDialog > .deleteOwnCardOuterButton').click();

    cy.get('.confirmOwnCardInput > .el-input__inner').type('222222');

    cy.get('.confirmOwnCardInnerButton').click();

    cy.get('.deleteOwnCardInnerButton').click({ multiple: true, force: true });

    cy.get('.cancelOwnCardInnerButton').click({ multiple: true, force: true });

    cy.get(':nth-child(3) > .outerDialog > .deleteOwnCardOuterButton').click();

    cy.get('.confirmOwnCardInput > .el-input__inner')
      .type('111111');

    cy.get('.confirmOwnCardInnerButton').click();

    cy.get('.deleteOwnCardInnerButton').click({ multiple: true, force: true });
  });

});
