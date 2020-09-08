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
import PlayerEntityPanel from '@/components/table/playerEntityPanel'
import Element from 'element-ui';

const localVue = createLocalVue();
localVue.use(Element);

describe('ItemEntityPanel.vue', () => {
  const wrapper = shallowMount(PlayerEntityPanel,
    {
      localVue,
      stubs:{
        'el-form': validateStub
      }
    });

  let mockAdapter = new MockAdapter(axios);
  let spyPost = jest.spyOn(axios, "post");

  mockAdapter.onAny().reply(200, mockData);

  it('Startup', async () => {
    await wrapper.vm.getList();
  });

  it('Player Entity Panel Nulls created',   async () => {
    expect(wrapper.vm.list).toBeNull();

    expect(spyPost).toHaveBeenCalledTimes(1);
  });

  it('Player Entity Panel Nulls confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;
    mockAdapter.onAny().reply(200, false);
    await wrapper.vm.confirmIdentity();
  });

  it('Player Entity Panel Nulls confirmIdentity result', async () => {
    expect(wrapper.vm.confirmDelete).toBeFalsy();
    expect(spyPost).toHaveBeenCalledTimes(2);
  });

  it('Player Entity Panel Nulls deleteData',  async () => {
    wrapper.vm.confirmDelete = true;
    wrapper.vm.panelVisible = true;
    wrapper.vm.deleteVisible = true;

    mockAdapter.onAny().reply(200, false);
    await wrapper.vm.deleteData();
  });

  it('Player Entity Panel Nulls deleteData result', async () => {
    expect(wrapper.vm.panelVisible).toBeTruthy();
    expect(wrapper.vm.deleteVisible).toBeTruthy();
    expect(spyPost).toHaveBeenCalledTimes(3);
  });

  it('Player Entity Panel Nulls createData', async () => {
    wrapper.vm.panelVisible = true;
    mockAdapter.onAny().reply(200, false);
    wrapper.vm.createData('temp');
    await wrapper.vm.submitCreate();
  });

  it('Player Entity Panel Nulls updateData', async () => {
    wrapper.vm.panelVisible = true;
    wrapper.vm.updateData('temp');
    await wrapper.vm.submitUpdate();
  });

  it('Player Entity Panel Nulls updateData result',  () => {
    expect(spyPost).toHaveBeenCalledTimes(5);
  });

});
