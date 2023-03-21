import React, { useState } from 'react'
import { Box, Tab, useTheme } from '@mui/material'
import { TabContext, TabList, TabPanel } from '@mui/lab'

import { tokens } from '../config/theme'

import RadarChart from '../components/dashboard/RadarChart'
import PieChart from '../components/dashboard/PieChart'
import CalendarChart from '../components/dashboard/CalendarChart'
import DailyProblemTable from '../components/dashboard/DailyProblemTable'
import SolvedProblemTable from '../components/dashboard/SolvedProblemTable'

const Dashboard = () => {
    const theme = useTheme()
    const colors = tokens(theme.palette.mode)

    const [tab, setTab] = useState('0')

    console.log(tab)

    return (
        <Box m="20px" height="100%">
            <TabContext value={tab}>
                <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                    <TabList
                        onChange={(e, v) => setTab(v)}
                        centered
                        sx={{
                            '& .MuiTabs-indicator': {
                                backgroundColor: colors.greenAccent['300'],
                            },
                        }}>
                        <Tab
                            label="통계"
                            value="0"
                            sx={{
                                '&.Mui-selected': {
                                    color: colors.greenAccent['300'],
                                },
                            }}
                        />
                        <Tab
                            label="해결한 문제"
                            value="1"
                            sx={{
                                '&.Mui-selected': {
                                    color: colors.greenAccent['300'],
                                },
                            }}
                        />
                        <Tab
                            label="오늘의 문제"
                            value="2"
                            sx={{
                                '&.Mui-selected': {
                                    color: colors.greenAccent['300'],
                                },
                            }}
                        />
                    </TabList>
                </Box>
                <TabPanel value="0" sx={{ width: '100%', height: '100%' }}>
                    <Box width="100%" height="30%" display="flex" justifyContent="center">
                        <CalendarChart />
                    </Box>
                    <Box width="100%" height="45%" display="flex" justifyContent="space-between">
                        <Box height="100%" width="30%" margin={0}>
                            <RadarChart />
                        </Box>
                        <Box height="100%" width="30%" margin={0}>
                            <PieChart />
                        </Box>
                    </Box>
                </TabPanel>
                <TabPanel value="1" sx={{ width: '100%', height: '100%' }}>
                    <SolvedProblemTable />
                </TabPanel>
                <TabPanel value="2" sx={{ width: '100%', height: '100%' }}>
                    <DailyProblemTable />
                </TabPanel>
            </TabContext>
        </Box>
    )
}

export default Dashboard
