package hello

import (
	"bytes"
	"crypto/ed25519"
	"fmt"
	"io"
	"net/http"
	"strconv"

	"golang.org/x/crypto/ssh"
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

// no-port-forwarding,no-X11-forwarding,no-agent-forwarding,no-pty,command="[[ $SSH_ORIGINAL_COMMAND =~ ^/opt/ssh_exec/.* ]] && bash -c $SSH_ORIGINAL_COMMAND" ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAILIIaeZjWZ8+QjMGAZ6MtgZ2uQGwhnjWNdjPm/0Hf00U aTest
var (
	sshUser = "admin"
	sshAddr = "127.0.0.1:22"
	pk      = ed25519.PrivateKey{
		0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
		0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
		0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17,
		0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f,
		0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27,
		0x28, 0x29, 0x2a, 0x2b, 0x2c, 0x2d, 0x2e, 0x2f,
		0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37,
		0x38, 0x39, 0x3a, 0x3b, 0x3c, 0x3d, 0x3e, 0x3f,
	}
)

func SshExec(cmd string) (string, error) {
	signer, err := ssh.NewSignerFromKey(&pk)
	if err != nil {
		return "", err
	}

	client, err := ssh.Dial("tcp", sshAddr, &ssh.ClientConfig{
		User:            sshUser,
		Auth:            []ssh.AuthMethod{ssh.PublicKeys(signer)},
		HostKeyCallback: ssh.InsecureIgnoreHostKey(),
	})
	if err != nil {
		return "", err
	}
	defer client.Close()

	session, err := client.NewSession()
	if err != nil {
		return "", err
	}
	defer session.Close()

	result, err := session.Output("/opt/ssh_exec/" + cmd)
	if err != nil {
		return "", err
	}
	return string(bytes.TrimSpace(result)), nil
}
