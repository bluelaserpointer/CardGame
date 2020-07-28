jest.mock('axios', () => ({
  get: jest.fn(() => Promise.resolve({data: [{
      mailId: 0,
      mailName: '',
      mailDetails: {
        mailImg: '',
        mailDescription: '',
      }
    }]})),
  post: jest.fn(() => Promise.resolve())
}));

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import MailEntityPanel from '@/components/edit/MailEntityPanel'
import Element from 'element-ui';
import moment from 'moment';
import axios from 'axios';

const localVue = createLocalVue();
localVue.use(Element);

describe('MailEntityPanel.vue', () => {
  const wrapper = shallowMount(MailEntityPanel,
    {
      localVue
    });

  it('Mail Entity Panel Resolves created getList watchList', async () => {
    expect(wrapper.vm.panelVisible).toBeFalsy();
    expect(wrapper.vm.list).toStrictEqual([{
      mailId: 0,
      mailName: '',
      mailImg: '',
      mailDescription: '',
      mailDetails: {
        mailImg: '',
        mailDescription: '',
      }
    }]);
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toBeCalledWith('http://localhost:8080/mail/getAllMails');
  });

  it('Mail Entity Panel Resolves handleUpdate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleUpdate();

    expect(wrapper.vm.panelVisible).toBeTruthy();
    expect(wrapper.vm.dialogStatus).toBe('update');
  });
});
