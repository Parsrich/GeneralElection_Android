package lets.vote.generalelection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class PartyWebInfoFragment extends Fragment {
    static PartyWebInfoFragment instance;
    private WebView infoWebView; // 웹뷰 선언
    private WebSettings mWebSettings; //웹뷰세팅


    public PartyWebInfoFragment() {
        // Required empty public constructor
    }

    public static PartyWebInfoFragment getInstance() {
        if (instance == null) {
            instance = new PartyWebInfoFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_candidate_web_info, container, false);
        String keyword = getArguments().getString("keyword");
        ViewPager viewPager = getActivity().findViewById(R.id.viewPager);
        viewPager.setPadding(
                0,
                getArguments().getInt("top"),
                0,
                getArguments().getInt("bottom"));
        viewPager.requestLayout();

        infoWebView = rootView.findViewById(R.id.infoWebView);

        infoWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        mWebSettings = infoWebView.getSettings(); //세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(false); // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false); // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(false); // 로컬저장소 허용 여부

        infoWebView.loadUrl("https://m.search.zum.com/search.zum?method=news&qm=f_typing.news&query="+keyword); // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작
        return rootView;
    }

    @Override
    public void onDetach() {
        ViewPager viewPager = getActivity().findViewById(R.id.viewPager);
        viewPager.setPadding(
                getArguments().getInt("left"),
                getArguments().getInt("top"),
                getArguments().getInt("right"),
                getArguments().getInt("bottom"));
        viewPager.requestLayout();
        super.onDetach();
    }
}
