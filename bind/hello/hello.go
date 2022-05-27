package hello

import (
	"bytes"
	"fmt"
	"io"
	"net/http"
	"strconv"
)

func Greetings(name string) string {
	return "Hello, " + name + "!"
}

func GetCnt() (int64, error) {
	resp, err := http.Get("https://counter.whoisnian.com/cnt")
	if err != nil {
		return 0, err
	} else if resp.StatusCode != 200 {
		return 0, fmt.Errorf(`"%s %s" received "%s"`, resp.Request.Method, resp.Request.URL.String(), resp.Status)
	}
	defer resp.Body.Close()
	return readAsInt(resp.Body)
}

func IncCnt() (int64, error) {
	resp, err := http.Get("https://counter.whoisnian.com/inc")
	if err != nil {
		return 0, err
	} else if resp.StatusCode != 200 {
		return 0, fmt.Errorf(`"%s %s" received "%s"`, resp.Request.Method, resp.Request.URL.String(), resp.Status)
	}
	defer resp.Body.Close()
	return readAsInt(resp.Body)
}

func readAsInt(r io.Reader) (int64, error) {
	buf, err := io.ReadAll(r)
	if err != nil {
		return 0, err
	}
	return strconv.ParseInt(string(buf), 10, 64)
}

func ProcessCommandApdu(in []byte) []byte {
	if bytes.Equal(in, []byte{0x00, 0xB0, 0x96, 0x00, 0x00}) {
		return []byte("processCommandApdu successfully\x90\x00")
	} else {
		return []byte{0x90, 0x00}
	}
}
