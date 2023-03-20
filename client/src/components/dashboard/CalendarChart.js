import React from 'react'
import { useTheme } from '@mui/material'
import { ResponsiveCalendar } from '@nivo/calendar'

import { SolvedCountByDate } from '../../data/mockData'
import { tokens } from '../../config/theme'

const CalendarChart = () => {
    const theme = useTheme()
    const colors = tokens(theme.palette.mode)

    return (
        <ResponsiveCalendar
            data={SolvedCountByDate}
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
