import React, { useState, useEffect } from 'react'
import { useTheme } from '@mui/material'
import { ResponsiveRadar } from '@nivo/radar'
import axios from 'axios'
import { useSetRecoilState } from 'recoil'

import { tokens } from '../../config/theme'
import { userState } from '../../atom'

const RadarChart = () => {
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

            const {
                data: { code, data },
            } = await axios.get(`${process.env.REACT_APP_SERVER_URL}/stat/tag`, {
                headers: {
                    Authorization: `Bearer ${token}`,
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
        <ResponsiveRadar
            data={data}
            theme={{
                textColor: colors.grey['100'],
                tooltip: {
                    container: {
                        color: colors.grey['400'],
                    },
                },
            }}
            sliceTooltip={(e) => <div>{e.data[0].formattedValue}</div>}
            keys={['count']}
            indexBy="tag"
            margin={{ top: 40, right: 90, left: 60 }}
            borderColor={{ from: 'background' }}
            gridLabelOffset={36}
            gridShape="linear"
            enableDots={false}
            colors={colors.greenAccent['600']}
            fillOpacity={1}
            blendMode={theme.palette.mode === 'dark' ? 'screen' : 'multiply'}
            motionConfig="wobbly"
        />
    )
}

export default RadarChart
