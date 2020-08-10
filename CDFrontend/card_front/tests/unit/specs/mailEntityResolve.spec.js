let mockData = { result: [{
    mailId: 0,
    mailName: '',
    mailDetails: {
      mailImg: '',
      mailDescription: '',
    }
  }]};

let mockDataTrans = [{
  mailId: 0,
  mailName: '',
  mailImg: '',
  mailDescription: '',
  mailDetails: {
    mailImg: '',
    mailDescription: '',
  }
}];

const validateStub = {
  render: () => {},
  methods: {
    validate: () => {}
  }
};

jest.unmock('axios');
import axios from 'axios';
import MockAdapter from "axios-mock-adapter";

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import MailEntityPanel from '@/components/article/MailEntityPanel'
import Element from 'element-ui';

const localVue = createLocalVue();
localVue.use(Element);

describe('MailEntityPanel.vue', () => {
  const wrapper = shallowMount(MailEntityPanel,
    {
      localVue,
      stubs:{
        'el-form': validateStub
      }
    });

  let mockAdapter = new MockAdapter(axios);
  let spyPost = jest.spyOn(axios, "post");

  mockAdapter.onPost('mail/List').reply(200, mockData);

  it('Startup', async () => {
    await wrapper.vm.getList();
  });

  it('Mail Entity Panel Resolves created getList watchList', async () => {
    expect(wrapper.vm.panelVisible).toBeFalsy();
    expect(wrapper.vm.list).toStrictEqual(mockDataTrans);
    expect(spyPost).toHaveBeenCalledTimes(1);
  });

  it('Mail Entity Panel Resolves handleUpdate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleUpdate();

    expect(wrapper.vm.panelVisible).toBeTruthy();
    expect(wrapper.vm.dialogStatus).toBe('update');
  });

  it('Mail Entity Panel Resolves Rest', () => {
    wrapper.vm.handleFilter();
    wrapper.vm.sortChange({prop: 'id'});
    wrapper.vm.sortByID('ascending');
  });
});
