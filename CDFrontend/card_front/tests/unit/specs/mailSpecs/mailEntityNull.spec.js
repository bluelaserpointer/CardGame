jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve({data: false})),
  post: jest.fn(() => Promise.resolve({data: false})),
}));

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import MailEntityPanel from '@/components/edit/MailEntityPanel'
import Element from 'element-ui';
import axios from 'axios';

const localVue = createLocalVue();
localVue.use(Element);

describe('MailEntityPanel.vue', () => {
  const wrapper = shallowMount(MailEntityPanel,
    {
      localVue
    });

  it('Mail Entity Panel Nulls created getList watchList', async () => {
    expect(wrapper.vm.panelVisible).toBeFalsy();
    expect(wrapper.vm.list).toStrictEqual(null);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith('http://localhost:8080/mail/getAllMails');
  });

});
