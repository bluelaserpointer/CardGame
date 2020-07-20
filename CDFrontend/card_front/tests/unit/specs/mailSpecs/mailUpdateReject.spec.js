jest.mock('axios', () => ({
  get: jest.fn(() => Promise.reject()),
  post: jest.fn(() => Promise.reject())
}));

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import MailUpdatePanel from '@/components/edit/MailUpdatePanel'
import Element from 'element-ui';
import axios from 'axios';

const localVue = createLocalVue();
localVue.use(Element);

describe('MailUpdatePanel.vue', () => {
  const wrapper = mount(MailUpdatePanel, {
    localVue,
    propsData:{
      updateContent: {
        mail: 0,
        mailImg : '0',
        mailName : '0',
        mailDescription : '0',
      }
    }
  });

  it('Mail Update Panel Rejects submitForm', () => {
    wrapper.vm.limit = false;
    expect(axios.post).toHaveBeenCalledTimes(0);
    wrapper.vm.submitForm();
    expect(axios.post).toHaveBeenCalledTimes(1);
  });

  it('Mail Entity Panel Rejects confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;

    await wrapper.vm.confirmIdentity();

    expect(wrapper.vm.confirmDelete).toBeFalsy();
    expect(axios.post).toHaveBeenCalledTimes(2);
  });

  it('Mail Entity Panel Rejects deleteData',  async () => {
    wrapper.vm.confirmDelete = true;
    wrapper.vm.panelVisible = true;
    wrapper.vm.deleteVisible = true;

    expect(axios.post).toHaveBeenCalledTimes(2);
    expect(wrapper.vm.confirmDelete).toBeTruthy();

    await wrapper.vm.deleteData();

    expect(wrapper.vm.panelVisible).toBeTruthy();
    expect(wrapper.vm.deleteVisible).toBeTruthy();
    expect(axios.post).toHaveBeenCalledTimes(3);
  });
});
