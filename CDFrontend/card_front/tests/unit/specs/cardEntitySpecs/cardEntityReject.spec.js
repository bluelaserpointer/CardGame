let mockData = false;

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
import CardEntityPanel from '@/components/table/cardEntityPanel'
import Element from 'element-ui';

const localVue = createLocalVue();
localVue.use(Element);

describe('CardEntityPanel.vue', () => {
  const wrapper = shallowMount(CardEntityPanel,
    {
      localVue,
      stubs:{
        'el-form': validateStub
      }
    });

  let mockAdapter = new MockAdapter(axios);
  let spyPost = jest.spyOn(axios, "post");

  mockAdapter.onPost('card/List').reply(200, mockData);

  it('Startup', async () => {
    await wrapper.vm.getList();
  });

  it('Card Entity Panel Resolves created',   async () => {
    expect(wrapper.vm.list).toBeNull();

    expect(spyPost).toHaveBeenCalledTimes(1);
  });

  it('Card Entity Panel Resolves confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;
    mockAdapter.onPost('user/confirmDelete').reply(200, false);
    await wrapper.vm.confirmIdentity();
  });

  it('Card Entity Panel Resolves confirmIdentity result', async () => {
    expect(wrapper.vm.confirmDelete).toBeFalsy();
    expect(spyPost).toHaveBeenCalledTimes(2);
  });

  it('Card Entity Panel Resolves deleteData',  async () => {
    wrapper.vm.confirmDelete = true;
    wrapper.vm.panelVisible = true;
    wrapper.vm.deleteVisible = true;

    mockAdapter.onPost('card/deleteCard').reply(200, false);
    await wrapper.vm.deleteData();
  });

  it('Card Entity Panel Resolves deleteData result', async () => {
    expect(wrapper.vm.panelVisible).toBeTruthy();
    expect(wrapper.vm.deleteVisible).toBeTruthy();
    expect(spyPost).toHaveBeenCalledTimes(3);
  });

  it('Card Entity Panel Resolves createData', async () => {
    wrapper.vm.panelVisible = true;
    mockAdapter.onAny().reply(200, false);
    wrapper.vm.createData('temp');
    await wrapper.vm.submitCreate();
  });

  it('Card Entity Panel Resolves updateData', async () => {
    wrapper.vm.panelVisible = true;
    wrapper.vm.updateData('temp');
    await wrapper.vm.submitUpdate();
  });

  it('Card Entity Panel Resolves updateData result',  () => {
    expect(spyPost).toHaveBeenCalledTimes(5);
  });

});
