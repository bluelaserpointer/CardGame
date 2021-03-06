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
import ItemEntityPanel from '@/components/table/itemEntityPanel'
import Element from 'element-ui';

const localVue = createLocalVue();
localVue.use(Element);

describe('ItemEntityPanel.vue', () => {
  const wrapper = shallowMount(ItemEntityPanel,
    {
      localVue,
      stubs:{
        'el-form': validateStub
      }
    });

  let mockAdapter = new MockAdapter(axios);
  let spyPost = jest.spyOn(axios, "post");

  mockAdapter.onPost('item/List').reply(400, mockData);

  it('Startup', async () => {
    await wrapper.vm.getList();
  });

  it('Item Entity Panel Rejects created',   async () => {
    expect(wrapper.vm.list).toBeNull();

    expect(spyPost).toHaveBeenCalledTimes(1);
  });

  it('Item Entity Panel Rejects confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;
    mockAdapter.onPost('user/confirmDelete').reply(400, false);
    await wrapper.vm.confirmIdentity();
  });

  it('Item Entity Panel Rejects confirmIdentity result', async () => {
    expect(wrapper.vm.confirmDelete).toBeFalsy();
    expect(spyPost).toHaveBeenCalledTimes(2);
  });

  it('Item Entity Panel Rejects deleteData',  async () => {
    wrapper.vm.confirmDelete = true;
    wrapper.vm.panelVisible = true;
    wrapper.vm.deleteVisible = true;

    mockAdapter.onPost('item/deleteItem').reply(400, false);
    await wrapper.vm.deleteData();
  });

  it('Item Entity Panel Rejects deleteData result', async () => {
    expect(wrapper.vm.panelVisible).toBeTruthy();
    expect(wrapper.vm.deleteVisible).toBeTruthy();
    expect(spyPost).toHaveBeenCalledTimes(3);
  });

  it('Item Entity Panel Rejects createData', async () => {
    wrapper.vm.panelVisible = true;
    mockAdapter.onAny().reply(400, false);
    wrapper.vm.createData('temp');
    await wrapper.vm.submitCreate();
  });

  it('Item Entity Panel Rejects updateData', async () => {
    wrapper.vm.panelVisible = true;
    wrapper.vm.updateData('temp');
    await wrapper.vm.submitUpdate();
  });

  it('Item Entity Panel Rejects updateData result',  () => {
    expect(spyPost).toHaveBeenCalledTimes(5);
  });

});
