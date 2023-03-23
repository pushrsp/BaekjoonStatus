import React, { useState, useEffect } from 'react'
import { useTheme } from '@mui/material'
import { ResponsiveCalendar } from '@nivo/calendar'
import axios from 'axios'
import { useSetRecoilState } from 'recoil'

import { tokens } from '../../config/theme'
import { userState } from '../../atom'

const CalendarChart = () => {
    const theme = useTheme()
    const colors = tokens(theme.palette.mode)
    const [data, setData] = useState([])
    const setUser = useSetRecoilState(userState)

    useEffect(() => {
        ;(async () => {
            const token = window.localStorage.getItem('@token')
            if (token === null) {
                setUser({})
                return
            }

            const now = new Date() // 현재 시간
            const utcNow = now.getTime() + now.getTimezoneOffset() * 60 * 1000
            const koreaTimeDiff = 9 * 60 * 60 * 1000
            const koreaNow = new Date(utcNow + koreaTimeDiff)

            const {
                data: { code, data },
            } = await axios.get(`${process.env.REACT_APP_SERVER_URL}/stat/date`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
                params: {
                    year: koreaNow.getFullYear(),
                },
            })

            if (code === '0000') {
                setData(data)
            } else {
                setUser({})
            }
        })()
    }, [])

    return (
        <ResponsiveCalendar
            data={data}
            theme={{
                textColor: colors.grey['100'],
                tooltip: {
                    container: {
                        color: colors.grey['400'],
                    },
                },
            }}
            from="2023-01-01"
            to="2023-12-31"
            emptyColor="#eeeeee"
            colors={['#94e2cd', '#70d8bd', '#4cceac', '#3da58a']}
            margin={{ top: 20, right: 60, left: 60 }}
            yearSpacing={40}
            monthBorderColor="#ffffff"
            dayBorderColor="#ffffff"
            isInteractive={true}
            align="top"
            legends={[
                {
                    anchor: 'bottom-right',
                    direction: 'row',
                    translateY: 36,
                    itemCount: 4,
                    itemWidth: 42,
                    itemHeight: 36,
                    itemsSpacing: 14,
                    itemDirection: 'right-to-left',
                },
            ]}
        />
    )
}

export default CalendarChart
