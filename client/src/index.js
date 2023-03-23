import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import { RecoilRoot } from 'recoil'
import { ProSidebarProvider } from 'react-pro-sidebar'
import { ToastContainer } from 'react-toastify'

import 'react-toastify/dist/ReactToastify.css'
import './index.css'

import App from './App'

const root = ReactDOM.createRoot(document.getElementById('root'))
root.render(
    <RecoilRoot>
        <BrowserRouter>
            <ToastContainer
                theme="colored"
                pauseOnHover={false}
                draggable={false}
                autoClose={1300}
            />
            <ProSidebarProvider>
                <App />
            </ProSidebarProvider>
        </BrowserRouter>
    </RecoilRoot>,
)
